import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ApiService } from '../../../services/api/api.service';
import { firstValueFrom } from 'rxjs';
import { startOfWeek, endOfWeek, isWithinInterval, parseISO } from 'date-fns';

interface Service {
  id: string;
  serviceName: string;
  servicePrice: number;
}

interface Appointment {
  id: string;
  appointmentDate: string;
  status: string;
  services: Service[];
}

interface ServiceModal {
  id: string;
  name: string;
  price: number;
}

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
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
  today: string = new Date().toISOString().split('T')[0];
  newOption: string | null = null;
  appointmentUpdate: Appointment | null = null;

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadServices();
    this.loadAppointments();
  }

  loadServices(): void {
    this.isLoading = true;
    this.apiService.getDataServices().subscribe({
      next: (response) => {
        this.services = response.data;
        console.log('Serviços carregados:', this.services);
      },
      error: (err) => console.error('Erro ao carregar serviços:', err),
      complete: () => (this.isLoading = false),
    });
  }

  loadAppointments(): void {
    this.apiService.getAllAppointments().subscribe({
      next: (appointments: Appointment[]) => {
        this.appointments = appointments;
      },
      error: (err) => console.error('Erro ao carregar agendamentos:', err),
    });
  }

  openModal(): void {
    this.isOpen = true;
  }

  openUpdateAppointmentModal(): void {
    this.modalUpdateAppointmentIsOpen = true;
  }

  closeUpdateAppointmentModal(): void {
    this.modalUpdateAppointmentIsOpen = false;
  }

  closeModal(): void {
    this.selectedOptions = [];
    this.isOpen = false;
  }

  addOption(option: string | null): void {
    if (option) {
      this.selectedOptions.push(option);
      this.newOption = null;
    }
  }

  getServiceName(optionId: string): string {
    const service = this.services.find((service) => service.id === optionId);
    return service ? service.name : 'Serviço não encontrado';
  }

  removeOption(option: string): void {
    this.selectedOptions = this.selectedOptions.filter(
      (selected) => selected !== option
    );
  }

  isSelected(option: string): boolean {
    return this.selectedOptions.includes(option);
  }

  isAppointmentInSameWeek(newDate: string): boolean {
    const newAppointmentDate = parseISO(newDate);
    const startOfNewWeek = startOfWeek(newAppointmentDate, { weekStartsOn: 1 });
    const endOfNewWeek = endOfWeek(newAppointmentDate, { weekStartsOn: 1 });

    return this.appointments.some((appointment) => {
      const appointmentDate = parseISO(appointment.appointmentDate);
      return isWithinInterval(appointmentDate, {
        start: startOfNewWeek,
        end: endOfNewWeek,
      });
    });
  }

  deleteAppointment(appointment: Appointment): void {
    const appointmentDate = parseISO(appointment.appointmentDate);
    const today = new Date();
    const twoDaysBefore = new Date(appointmentDate);
    twoDaysBefore.setDate(appointmentDate.getDate() - 2);

    if (today > twoDaysBefore) {
      alert(
        'Cancelar de agendamento com menos de 2 dias de antecedência somente por telefone.'
      );
      return;
    }

    this.isLoading = true;
    this.apiService.deleteAppointment(appointment.id).subscribe({
      next: () => {
        console.log('Agendamento deletado com sucesso');
        this.loadAppointments();
      },
      error: (err) => {
        console.error('Erro ao excluir o agendamento:', err);
        alert('Ocorreu um erro ao tentar excluir o agendamento.');
      },
      complete: () => (this.isLoading = false),
    });
  }

  updateAppointment(appointment: Appointment): void {
    this.appointmentUpdate = appointment;
    this.openUpdateAppointmentModal();
  }

  async handleSubmitUpdate(event: Event): Promise<void> {
    event.preventDefault();
    const form = event.target as HTMLFormElement;
    console.log('Entrei no handleSubmitUpdate');
    console.log(this.appointmentUpdate);
    const updatedAppointment = {
      id: this.appointmentUpdate?.id,
      appointmentDate: (event.target as HTMLFormElement)['date'].value,
      services: this.selectedOptions.map((id) => ({
        id: id,
      })),
    };
    console.log('Depois');
    console.log(updatedAppointment);
  }

  async handleSubmit(event: Event): Promise<void> {
    event.preventDefault();
    const form = event.target as HTMLFormElement;
    const date = (form.elements.namedItem('date') as HTMLInputElement).value;

    if (this.isAppointmentInSameWeek(date)) {
      alert('Você já possui um agendamento marcado nesta semana.');
      return;
    }

    this.isLoading = true;

    try {
      const serviceIds = this.selectedOptions;

      const data = {
        serviceId: serviceIds,
        appointmentDate: date,
      };

      console.log('Tentando enviar os dados:', data);

      const response = await firstValueFrom(
        this.apiService.createAppointment(data)
      );

      if (response.status === 200) {
        this.loadAppointments();
        this.closeModal();
      }
    } catch (error) {
      console.error('Erro ao agendar serviço:', error);
    } finally {
      this.loadAppointments();
      this.isLoading = false;
      this.closeModal();
    }
  }
}
