export interface Service {
  id: string;
  serviceName: string;
  servicePrice: number;
}

export interface Appointment {
  id: string;
  appointmentDate: string;
  status: string;
  services: Service[];
}

export interface ServiceModal {
  id: string;
  name: string;
  price: number;
}
