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
import { AppointmentTableComponent } from '../../../components/appointment-table/appointment-table.component';
import { CreateAppointmentFormComponent } from '../../../components/create-appointment-form/create-appointment-form.component';
import {
  Service,
  Appointment,
  ServiceModal,
  RequestUpadateAppointment,
  RequestCreateAppointment,
} from '../../../types';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonComponent,
    AppointmentModalComponent,
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
  newServiceIdAppointmentUpdate: string[] = [];
  newDateAppointmentUpdate: string = '';
  newSelectedTimeAppointmentUpdate: string = '';
  newAppointmentIdUpdate: string = '';
  selectedTime: string = '12:00 AM';
  showAlert: boolean = false;
  alertMessage: string = '';
  alertResponse: boolean | null = null;
  filteredAppointments: Appointment[] = [];
  startDateFilterAppointments: string = '';
  endDateFilterAppointments: string = '';

  constructor(private apiService: ApiService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initializeData();
  }

  private initializeData(): void {
    this.loadServices();
    this.loadAppointments();
  }

  confirmAlert() {
    this.alertResponse = true;
    this.showAlert = false;
    this.closeModal();
  }
  cancelAlert() {
    this.alertResponse = false;
    this.showAlert = false;
  }

  filterAppointments() {
    if (this.startDateFilterAppointments && this.endDateFilterAppointments) {
      const start = new Date(this.startDateFilterAppointments);
      const end = new Date(this.endDateFilterAppointments);
      this.filteredAppointments = this.appointments.filter((appointment) => {
        const appointmentDate = new Date(appointment.appointmentDate);
        return appointmentDate >= start && appointmentDate <= end;
      });
    }
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

  private loadServices(): void {
    this.apiService.getDataServices().subscribe({
      next: (response) => {
        this.services = response.data;
      },
      error: (err) => console.error('Erro ao carregar serviços:', err),
    });
  }

  private loadAppointments(): void {
    this.apiService.getAllAppointments().subscribe({
      next: (appointments) => {
        this.appointments = appointments.data;
      },
      error: (err) => console.error('Erro ao carregar agendamentos:', err),
    });
  }

  openModal(): void {
    this.isOpen = true;
  }

  closeModal(): void {
    this.isOpen = false;
  }

  openUpdateAppointmentModal(): void {
    this.modalUpdateAppointmentIsOpen = true;
  }

  closeUpdateAppointmentModal(): void {
    this.selectedOptions = [];
    this.modalUpdateAppointmentIsOpen = false;
  }

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

  private getAppointmentsInSameWeek(newDate: string): any[] {
    const newAppointmentDate = parseISO(newDate);
    const weekInterval = {
      start: startOfWeek(newAppointmentDate, { weekStartsOn: 0 }),
      end: endOfWeek(newAppointmentDate, { weekStartsOn: 0 }),
    };

    return this.appointments.filter((appointment) =>
      isWithinInterval(parseISO(appointment.appointmentDate), weekInterval)
    );
  }

  async handleSubmit(event: Event): Promise<void> {
    event.preventDefault();
    const form = event.target as HTMLFormElement;
    var date = (form.elements.namedItem('date') as HTMLInputElement).value;

    const appointmentsInSameWeek = await this.getAppointmentsInSameWeek(date);

    if (appointmentsInSameWeek.length > 0) {
      const firstAppointmentInSameWeek = appointmentsInSameWeek[0];

      const userConfirmed = await this.showAlertAsync(
        `Você já possui um agendamento para essa semana. ${firstAppointmentInSameWeek.appointmentDate}, Gostaria de marcar esse novo agendamento para a mesma data ?`
      );
      if (userConfirmed) {
        date = firstAppointmentInSameWeek.appointmentDate;
        const serviceIds = this.selectedOptions;
        const requestData: RequestCreateAppointment = {
          serviceId: serviceIds,
          appointmentDate: date,
          appointmentTime: this.selectedTime,
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
      } else {
        const serviceIds = this.selectedOptions;
        const requestData: RequestCreateAppointment = {
          serviceId: serviceIds,
          appointmentDate: date,
          appointmentTime: this.selectedTime,
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
    } else {
      const serviceIds = this.selectedOptions;
      const requestData: RequestCreateAppointment = {
        serviceId: serviceIds,
        appointmentDate: date,
        appointmentTime: this.selectedTime,
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
  }

  async cancelAppointment(appointment: Appointment): Promise<void> {
    const userConfirmed = await this.showAlertAsync(
      'Tem certeza que deseja cancelar este agendamento?'
    );
    if (userConfirmed) {
      const appointmentDate = parseISO(appointment.appointmentDate);
      const twoDaysBefore = new Date(appointmentDate);
      twoDaysBefore.setDate(appointmentDate.getDate() - 2);
      // Verifica se a data atual é maior que a data limite (2 dias antes do agendamento)
      if (new Date() > twoDaysBefore) {
        alert(
          'Cancelamento de agendamento com menos de 2 dias de antecedência somente por telefone.'
        );
        return;
      }

      this.fetchWithLoading(
        () => firstValueFrom(this.apiService.cancelAppointment(appointment.id)),
        () => {
          this.loadAppointments(); // Recarrega a lista de agendamentos
          alert('Agendamento cancelado com sucesso.');
        },
        () => alert('Erro ao cancelar o agendamento.')
      );
    } else {
      return;
    }
  }

  toggleTable(appointmentId: string) {
    const appointment = this.appointments.find((i) => i.id === appointmentId);
    if (appointment) {
      appointment.showTable = !appointment.showTable;
    }
  }

  updateAppointment(appointment: Appointment): void {
    this.newServiceIdAppointmentUpdate = appointment.services.map(
      (service) => service.id
    );
    this.newDateAppointmentUpdate = appointment.appointmentDate;
    this.newAppointmentIdUpdate = appointment.id;
    this.selectedOptions = appointment.services.map((service) => service.id);

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

    this.openUpdateAppointmentModal();
  }

  async handleUpdateSubmit(event: Event): Promise<void> {
    event.preventDefault();
    this.newServiceIdAppointmentUpdate = this.selectedOptions;
    const appointment: RequestUpadateAppointment = {
      serviceId: this.newServiceIdAppointmentUpdate,
      appointmentDate: this.newDateAppointmentUpdate,
      appointmentTime: this.newSelectedTimeAppointmentUpdate,
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

  async testeAlert(appointment: Appointment) {
    const userConfirmed = await this.showAlertAsync(
      'Tem certeza que deseja cancelar este agendamento?'
    );
    if (userConfirmed) {
      alert('Usuário confirmou');
    } else {
      alert('Usuário cancelou');
    }
  }

  showAlertAsync(message: string): Promise<boolean> {
    this.alertMessage = message;
    this.showAlert = true;

    return new Promise((resolve, reject) => {
      this.confirmAlert = () => {
        this.showAlert = false;
        this.alertResponse = true;
        resolve(true); // Resolve com "Sim"
      };

      this.cancelAlert = () => {
        this.showAlert = false;
        this.alertResponse = false;
        resolve(false); // Resolve com "Não"
      };
    });
  }
}
