import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  Form,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ApiService } from '../../../services/api/api.service';
import { firstValueFrom } from 'rxjs';
import { startOfWeek, endOfWeek, isWithinInterval, parseISO } from 'date-fns';
import { ButtonComponent } from '../../../components/ui/button/button.component';
import { AppointmentModalComponent } from '../../../components/appointment-modal/appointment-modal.component';
import { UpdatedAppointmentModalComponent } from '../../../components/updated-appointment-modal/updated-appointment-modal.component';
import { AppointmentTableComponent } from '../../../components/appointment-table/appointment-table.component';
import {
  Service,
  Appointment,
  ServiceModal,
  RequestUpadateAppointment,
  RequestCreateAppointment,
} from '../../../types';
import { CreateAppointmentFormComponent } from '../../../components/create-appointment-form/create-appointment-form.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonComponent,
    AppointmentModalComponent,
    UpdatedAppointmentModalComponent,
    AppointmentTableComponent,
    CreateAppointmentFormComponent,
  ],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  isOpen = false;
  modalUpdateAppointmentIsOpen = false;
  isLoading = false;
  searchText = '';
  selectedOptions: string[] = [];
  services: ServiceModal[] = [];
  appointments: Appointment[] = [];
  today = this.getTodayDate();
  newOption: string | null = null;
  newServiceIdAppointmentUpdate: string = '';
  newDateAppointmentUpdate: string = '';
  newAppointmentIdUpdate: string = '';

  constructor(private apiService: ApiService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initializeData();
  }

  // Métodos principais
  private initializeData(): void {
    this.loadServices();
    this.loadAppointments();
  }

  private getTodayDate(): string {
    return new Date().toISOString().split('T')[0];
  }

  private async fetchWithLoading<T>(
    fetchFunction: () => Promise<T>,
    onSuccess: (data: T) => void,
    onError: (error: any) => void
  ): Promise<void> {
    this.isLoading = true;
    try {
      const data = await fetchFunction();
      onSuccess(data);
    } catch (error) {
      console.error('Erro na operação:', error);
      onError(error);
    } finally {
      this.isLoading = false;
    }
  }

  // Serviços
  private loadServices(): void {
    this.apiService.getDataServices().subscribe({
      next: (response) => (this.services = response.data),
      error: (err) => console.error('Erro ao carregar serviços:', err),
    });
  }

  private loadAppointments(): void {
    this.apiService.getAllAppointments().subscribe({
      next: (appointments) => (this.appointments = appointments),
      error: (err) => console.error('Erro ao carregar agendamentos:', err),
    });
  }

  // Modal
  openModal(): void {
    this.isOpen = true;
  }

  closeModal(): void {
    this.selectedOptions = [];
    this.isOpen = false;
  }

  openUpdateAppointmentModal(): void {
    this.modalUpdateAppointmentIsOpen = true;
  }

  closeUpdateAppointmentModal(): void {
    this.modalUpdateAppointmentIsOpen = false;
  }

  // Manipulação de opções
  addOption(option: string | null): void {
    if (option) {
      this.selectedOptions.push(option);
      this.newOption = null;
    }
  }

  removeOption(option: string): void {
    this.selectedOptions = this.selectedOptions.filter(
      (selected) => selected !== option
    );
  }

  isSelected(option: string): boolean {
    return this.selectedOptions.includes(option);
  }

  getServiceName(optionId: string): string {
    const service = this.services.find((service) => service.id === optionId);
    return service ? service.name : 'Serviço não encontrado';
  }

  // Validação de agendamento
  private getAppointmentsInSameWeek(newDate: string): any[] {
    const newAppointmentDate = parseISO(newDate);
    const weekInterval = {
      start: startOfWeek(newAppointmentDate, { weekStartsOn: 1 }),
      end: endOfWeek(newAppointmentDate, { weekStartsOn: 1 }),
    };

    // Filtra todos os agendamentos dentro da mesma semana
    return this.appointments.filter((appointment) =>
      isWithinInterval(parseISO(appointment.appointmentDate), weekInterval)
    );
  }
  // Agendamento
  async handleSubmit(event: Event): Promise<void> {
    event.preventDefault();
    const form = event.target as HTMLFormElement;
    var date = (form.elements.namedItem('date') as HTMLInputElement).value;

    const appointmentsInSameWeek = this.getAppointmentsInSameWeek(date);

    if (appointmentsInSameWeek.length > 0) {
      // Exibe o primeiro agendamento da semana
      const firstAppointment = appointmentsInSameWeek[0];
      var result = confirm(
        `Você já possui um agendamento marcado nesta semana no dia, ${firstAppointment.appointmentDate} gostaria de marcar esse agendamento no mesmo dia?`
      );
      if (result) {
        date = firstAppointment.appointmentDate;
      } else {
        return;
      }
    }

    const serviceIds = this.selectedOptions;
    const requestData: RequestCreateAppointment = {
      serviceId: serviceIds,
      appointmentDate: date,
    };

    await this.fetchWithLoading(
      () => firstValueFrom(this.apiService.createAppointment(requestData)),
      () => {
        alert('Agendamento realizado com sucesso.');
        this.loadAppointments();
        this.closeModal();
      },
      () => alert('Erro ao agendar serviço.')
    );
  }

  cancelAppointment(appointment: Appointment): void {
    const result = confirm('Deseja realmente cancelar o agendamento?');
    if (result) {
      const appointmentDate = parseISO(appointment.appointmentDate); // Converte a data do agendamento
      const twoDaysBefore = new Date(appointmentDate);
      twoDaysBefore.setDate(appointmentDate.getDate() - 2); // Subtrai 2 dias da data do agendamento

      // Verifica se a data atual é maior que a data limite (2 dias antes do agendamento)
      if (new Date() > twoDaysBefore) {
        alert(
          'Cancelamento de agendamento com menos de 2 dias de antecedência somente por telefone.'
        );
        return;
      }

      // Chama o serviço para cancelar o agendamento
      this.fetchWithLoading(
        () => firstValueFrom(this.apiService.cancelAppointment(appointment.id)),
        () => {
          this.loadAppointments(); // Recarrega a lista de agendamentos
          alert('Agendamento cancelado com sucesso.');
        },
        () => alert('Erro ao cancelar o agendamento.')
      );
    } else {
      return; // Se o usuário cancelar a confirmação, nada acontece
    }
  }

  // cancelAppointment(appointment: Appointment): void {
  //   var result = confirm('Deseja realmente cancelar o agendamento?');
  //   if (result) {
  //     const appointmentDate = parseISO(appointment.appointmentDate);
  //     const twoDaysBefore = new Date(appointmentDate);
  //     twoDaysBefore.setDate(appointmentDate.getDate() - 2);

  //     if (new Date() > twoDaysBefore) {
  //       alert(
  //         'Cancelar de agendamento com menos de 2 dias de antecedência somente por telefone.'
  //       );
  //       return;
  //     }

  //     this.fetchWithLoading(
  //       () => firstValueFrom(this.apiService.cancelAppointment(appointment.id)),
  //       () => {
  //         this.loadAppointments();
  //         alert('Agendamento cancelado com sucesso.');
  //       },
  //       () => alert('Erro ao excluir o agendamento.')
  //     );
  //   } else {
  //     return;
  //   }
  // }

  updateAppointment(appointment: Appointment): void {
    this.newServiceIdAppointmentUpdate = appointment.services[0].id;
    this.newDateAppointmentUpdate = appointment.appointmentDate;
    this.newAppointmentIdUpdate = appointment.id;

    const appointmentDate = parseISO(appointment.appointmentDate);
    const twoDaysBefore = new Date(appointmentDate);
    twoDaysBefore.setDate(appointmentDate.getDate() - 2);

    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);

    if (currentDate > twoDaysBefore) {
      alert(
        'Alteração de agendamento com menos de 2 dias de antecedência somente por telefone.'
      );
      return;
    }

    // Abre o modal para editar o agendamento
    this.openUpdateAppointmentModal();
  }

  async handleUpdateSubmit(event: Event): Promise<void> {
    event.preventDefault();
    const appointment: RequestUpadateAppointment = {
      serviceId: [this.newServiceIdAppointmentUpdate],
      appointmentDate: this.newDateAppointmentUpdate,
    };

    await this.fetchWithLoading(
      () =>
        firstValueFrom(
          this.apiService.updateAppointment(
            this.newAppointmentIdUpdate,
            appointment
          )
        ),
      () => {
        alert('Agendamento atualizado com sucesso.');
        this.loadAppointments();
        this.closeUpdateAppointmentModal();
      },
      (err) => {
        console.error('Erro ao atualizar agendamento:', err);
        alert('Erro ao atualizar o agendamento.');
      }
    );
  }
}
