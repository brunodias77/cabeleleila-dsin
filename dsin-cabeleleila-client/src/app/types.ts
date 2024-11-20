interface Role {
  id: string;
  createdDate: string; // ou Date se for convertido
  updatedDate: string | null;
  name: string;
}
export interface UserDetails {
  data: {
    email: string;
    name: string;
    phoneNumber: string;
    role: Role[];
  };
}
export interface Service {
  id: string;
  serviceName: string;
  servicePrice: number;
}

export interface Appointment {
  id: string;
  appointmentDate: string;
  appointmentTime: string;
  status: string;
  services: Service[];
  showTable: boolean;
}

interface ServiceDTO {
  id: string;
  serviceName: string;
  servicePrice: number;
}

export interface AppointDetailsAdmin {
  id: string;
  appointmentDate: string; // Pode ser um formato de string ISO 8601
  appointmentTime: string; // Pode ser um formato de string como "HH:mm:ss"
  status: 'AGENDADO' | 'CANCELADO' | 'REALIZADO' | 'CONFIRMADO'; // Enum para status
  services: Service[]; // Lista de serviços
  clientName: string; // Nome do cliente
  clientEmail: string; // E-mail do cliente
  clientPhone: string; // Telefone do cliente
  showTable: boolean;
}

export interface AppointmentAdmin {
  status: number; // Status HTTP da resposta (por exemplo: 200)
  timestamp: string; // Timestamp da resposta
  data: AppointDetailsAdmin[]; // Lista de agendamentos
}

export interface ServiceModal {
  id: string;
  name: string;
  price: number;
}
export interface BaseResponseDTO<T> {
  status: number;
  message: string;
  data?: T;
}

export interface RequestCreateAppointment {
  serviceId: string[];
  appointmentDate: string;
  appointmentTime: string;
}

export interface RequestUpadateAppointment {
  serviceId: string[];
  appointmentTime: string;
  appointmentDate: string;
}
export interface RequestUpadateAppointmentAdmin {
  serviceId: string[];
  appointmentTime: string;
  appointmentDate: string;
}

export interface RequestWeeklyPerformanceAdmin {
  startDate: string;
  endDate: string;
}

export interface WeeklyPerformance {
  totalScheduledServices: number;
  totalCancelledServices: number;
  totalRevenue: number;
}

export interface RequestCreateServiceAdmin {
  name: string;
  description: string;
  price: number;
}
