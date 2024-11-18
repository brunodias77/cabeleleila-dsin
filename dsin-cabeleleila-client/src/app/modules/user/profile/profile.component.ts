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
import { Service, Appointment, ServiceModal } from '../../../types';
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
  appointmentUpdate: Appointment | null = null;
  updatedForm: FormGroup;

  constructor(private apiService: ApiService, private fb: FormBuilder) {
    this.updatedForm = this.fb.group({
      appointmentDate: [
        this.appointmentUpdate?.appointmentDate,
        Validators.required,
      ],
      services: [[], Validators.required],
    });
  }

  ngOnInit(): void {
    this.initializeData();
  }

  // Métodos principais
  private initializeData(): void {
    this.loadServices();
    this.loadAppointments();
    console.log('Services', this.services);
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
  private isAppointmentInSameWeek(newDate: string): boolean {
    const newAppointmentDate = parseISO(newDate);
    const weekInterval = {
      start: startOfWeek(newAppointmentDate, { weekStartsOn: 1 }),
      end: endOfWeek(newAppointmentDate, { weekStartsOn: 1 }),
    };

    return this.appointments.some((appointment) =>
      isWithinInterval(parseISO(appointment.appointmentDate), weekInterval)
    );
  }

  // Agendamento
  async handleSubmit(event: Event): Promise<void> {
    event.preventDefault();
    const form = event.target as HTMLFormElement;
    const date = (form.elements.namedItem('date') as HTMLInputElement).value;

    if (this.isAppointmentInSameWeek(date)) {
      alert('Você já possui um agendamento marcado nesta semana.');
      return;
    }

    const serviceIds = this.selectedOptions;
    const requestData = { serviceId: serviceIds, appointmentDate: date };

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
    console.log('Entrei no Cancelamento !');
    const appointmentDate = parseISO(appointment.appointmentDate);
    const twoDaysBefore = new Date(appointmentDate);
    twoDaysBefore.setDate(appointmentDate.getDate() - 2);

    if (new Date() > twoDaysBefore) {
      alert(
        'Cancelar de agendamento com menos de 2 dias de antecedência somente por telefone.'
      );
      return;
    }
    console.log('id', appointment.id);

    this.fetchWithLoading(
      () => firstValueFrom(this.apiService.cancelAppointment(appointment.id)),
      () => {
        this.loadAppointments();
        alert('Agendamento cancelado com sucesso.');
      },
      () => alert('Erro ao excluir o agendamento.')
    );
  }

  updateAppointment(appointment: Appointment): void {
    this.appointmentUpdate = appointment;
    this.openUpdateAppointmentModal();
  }

  async handleUpdateSubmit(event: Event): Promise<void> {
    event.preventDefault();
  }
}
