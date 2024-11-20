import { Component, OnInit } from '@angular/core';
import { AppointmentTableComponent } from '../../../../components/appointment-table/appointment-table.component';
import {
  Appointment,
  AppointmentAdmin,
  RequestUpadateAppointmentAdmin,
  Service,
  ServiceModal,
} from '../../../../types';
import { startOfWeek, endOfWeek, isWithinInterval, parseISO } from 'date-fns';
import { firstValueFrom } from 'rxjs';
import { ApiService } from '../../../../services/api/api.service';
import { CommonModule } from '@angular/common';
import { AppointmentModalComponent } from '../../../../components/appointment-modal/appointment-modal.component';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../../components/ui/button/button.component';

@Component({
  selector: 'app-appointments-admin',
  standalone: true,
  imports: [
    AppointmentTableComponent,
    CommonModule,
    AppointmentModalComponent,
    FormsModule,
    ButtonComponent,
  ],
  templateUrl: './appointments-admin.component.html',
  styleUrl: './appointments-admin.component.scss',
})
export class AppointmentsAdminComponent implements OnInit {
  appointments: Appointment[] = [];
  appointmentUpdate_serviceId: string[] = [];
  appointmentUpdate_date: string = '';
  appointmentUpdate_time: string = '';
  appointmentUpdate_appointmntId: string = '';
  modalUpdateAppointmentIsOpen: boolean = false;
  today = this.getTodayDate();
  services: ServiceModal[] = [];
  isLoading = false;
  newOption: string | null = null;
  selectedOptions: string[] = [];

  constructor(private apiService: ApiService) {}
  ngOnInit(): void {
    this.getAppointmentsAdmin();
    this.loadServices();
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

  getServiceName(optionId: string): string {
    const service = this.services.find((service) => service.id === optionId);
    return service ? service.name : 'Serviço não encontrado';
  }

  toggleTable(appointmentId: string) {
    const appointment = this.appointments.find((i) => i.id === appointmentId);
    if (appointment) {
      appointment.showTable = !appointment.showTable;
    }
  }
  private getTodayDate(): string {
    return new Date().toISOString().split('T')[0];
  }

  private loadServices(): void {
    this.apiService.getDataServices().subscribe({
      next: (response) => {
        console.log('Serviços carregados:', response.data);
        this.services = response.data;
      },
      error: (err) => console.error('Erro ao carregar serviços:', err),
    });
  }

  getAppointmentsAdmin(): void {
    this.apiService.getAllAppointmentsAdmin().subscribe({
      next: (response) => {
        console.log('Agendamentos carregados:', response);
        // Acesse a propriedade 'data' da resposta corretamente
        this.appointments = response.data;
      },
      error: (err) => console.error('Erro ao carregar agendamentos:', err),
    });
  }

  statusClasses(status: string) {
    return (
      {
        AGENDADO:
          'text-green-600 bg-green-50 px-3 py-2 rounded-full font-semibold text-xs',
        CANCELADO:
          'text-red-600 bg-red-50 px-3 py-2 rounded-full font-semibold text-xs',
      }[status] || ''
    );
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

  updateAppointmentAdmin(appointment: Appointment) {
    this.appointmentUpdate_time = appointment.appointmentTime;
    this.appointmentUpdate_serviceId = appointment.services.map(
      (service) => service.id
    );
    this.appointmentUpdate_date = appointment.appointmentDate;
    this.appointmentUpdate_appointmntId = appointment.id;
    this.selectedOptions = appointment.services.map((service) => service.id);

    this.openUpdateAppointmentModal();
  }

  cancelAppointmentAdmin(appointment: Appointment) {}

  openUpdateAppointmentModal(): void {
    this.modalUpdateAppointmentIsOpen = true;
  }

  closeUpdateAppointmentModal(): void {
    this.modalUpdateAppointmentIsOpen = false;
  }

  async handleUpdateSubmit(event: Event) {
    event.preventDefault();
    this.appointmentUpdate_serviceId = this.selectedOptions;
    console.log(this.appointmentUpdate_serviceId);
    const appointment: RequestUpadateAppointmentAdmin = {
      serviceId: this.appointmentUpdate_serviceId,
      appointmentDate: this.appointmentUpdate_date,
      appointmentTime: this.appointmentUpdate_time,
    };

    console.log('Handle Update Submiit', appointment);

    await this.fetchWithLoading(
      () =>
        firstValueFrom(
          this.apiService.updateAppointmentAdmin(
            this.appointmentUpdate_appointmntId,
            appointment
          )
        ),
      () => {
        alert('Agendamento atualizado com sucesso.');
        this.getAppointmentsAdmin();
        this.closeUpdateAppointmentModal();
      },
      (err) => {
        console.error('Erro ao atualizar agendamento:', err);
        alert('Erro ao atualizar o agendamento.');
      }
    );
  }
}
