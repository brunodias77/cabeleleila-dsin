import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.scss'],
})
export class ToastComponent implements OnChanges {
  @Input() message: string = '';
  @Input() type: 'success' | 'error' = 'success';

  show = false;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['message'] && this.message) {
      this.show = true;
      setTimeout(() => {
        this.show = false;
      }, 3000); // O toast desaparece após 3 segundos
    }
  }
}
