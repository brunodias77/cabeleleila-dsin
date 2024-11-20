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

export interface AppointmentAdmin {
  status: number; // Status HTTP da resposta (por exemplo: 200)
  timestamp: string; // Timestamp da resposta
  data: Appointment[]; // Lista de agendamentos
}

export interface ServiceModal {
  id: string;
  name: string;
  price: number;
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

export interface BaseResponseDTO {
  status: number;
  message: string;
}
export interface WeeklyPerformance {
  totalScheduledServices: number;
  totalCancelledServices: number;
  totalRevenue: number;
}
