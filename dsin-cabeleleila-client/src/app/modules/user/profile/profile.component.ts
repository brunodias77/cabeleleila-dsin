import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ApiService } from '../../../services/api/api.service';
import { firstValueFrom } from 'rxjs';
import { startOfWeek, endOfWeek, isWithinInterval, parseISO } from 'date-fns';

interface Appointment {
  id: string;
  name: string;
  date: string;
  status: string;
  price: string;
}

interface Service {
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
  services: Service[] = [];
  appointments: Appointment[] = [];
  today: string = new Date().toISOString().split('T')[0];
  newOption: string | null = null;
  appointmentUpdate: Appointment | null = null;

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadServices();
    this.loadAppointments();
  }

  // Carrega os serviços disponíveis
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

  // Carrega os agendamentos do usuário
  loadAppointments(): void {
    this.apiService.getAllAppointments().subscribe({
      next: (appointments) => {
        this.appointments = appointments.map((appointment: any) => ({
          id: appointment.id,
          name: appointment.serviceName,
          date: appointment.appointmentDateTime,
          status: appointment.status,
          price: `$${appointment.servicePrice}`,
        }));
        console.log('Agendamentos carregados:', this.appointments);
      },
      error: (err) => console.error('Erro ao carregar agendamentos:', err),
    });
  }

  // Abre o modal de agendamento
  openModal(): void {
    this.isOpen = true;
  }

  openUpdateAppointmentModal(): void {
    this.modalUpdateAppointmentIsOpen = true;
  }

  closeUpdateAppointmentModal(): void {
    this.modalUpdateAppointmentIsOpen = false;
  }

  // Fecha o modal e limpa as opções selecionadas
  closeModal(): void {
    this.selectedOptions = [];
    this.isOpen = false;
  }

  // Adiciona uma opção ao array de selecionados
  addOption(option: string | null): void {
    if (option) {
      this.selectedOptions.push(option);
      this.newOption = null;
    }
  }

  // Retorna o nome do serviço baseado no ID
  getServiceName(optionId: string): string {
    const service = this.services.find((service) => service.id === optionId);
    return service ? service.name : 'Serviço não encontrado';
  }

  // Remove uma opção da seleção
  removeOption(option: string): void {
    this.selectedOptions = this.selectedOptions.filter(
      (selected) => selected !== option
    );
  }

  // Verifica se a opção foi selecionada
  isSelected(option: string): boolean {
    return this.selectedOptions.includes(option);
  }

  // Verifica se o novo agendamento está na mesma semana de um agendamento existente
  isAppointmentInSameWeek(newDate: string): boolean {
    const newAppointmentDate = parseISO(newDate);
    const startOfNewWeek = startOfWeek(newAppointmentDate, { weekStartsOn: 1 });
    const endOfNewWeek = endOfWeek(newAppointmentDate, { weekStartsOn: 1 });

    return this.appointments.some((appointment) => {
      const appointmentDate = parseISO(appointment.date);
      return isWithinInterval(appointmentDate, {
        start: startOfNewWeek,
        end: endOfNewWeek,
      });
    });
  }

  // Deleta um agendamento, com restrição de antecedência de 2 dias
  deleteAppointment(appointment: Appointment): void {
    const appointmentDate = parseISO(appointment.date);
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
    console.log('Entrei no updateAppointment');
    console.log(appointment.id);
    this.appointmentUpdate = appointment;
    this.openUpdateAppointmentModal();
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
      this.isLoading = false;
    }
  }
}
