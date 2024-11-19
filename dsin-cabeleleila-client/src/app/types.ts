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

export interface BaseResponseDTO {
  status: number;
  message: string;
}
export interface WeeklyPerformance {
  totalScheduledServices: number;
  totalCancelledServices: number;
  totalRevenue: number;
}
