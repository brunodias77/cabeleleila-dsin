import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss',
})
export class ButtonComponent {
  @Input() label: string = '';
  @Input() color: 'confirm' | 'cancel' | 'update' | 'default' = 'default';
  @Input() disabled: boolean = false;
  @Output() onClick = new EventEmitter<void>();

  get buttonClasses() {
    const base = 'px-4 py-2 rounded-lg font-bold';
    const colors = {
      confirm: 'bg-green-500 hover:bg-green-600 text-white',
      cancel: 'bg-red-600 hover:bg-red-700 text-white',
      update: 'bg-yellow-500 hover:bg-yellow-600 text-white',
      default: 'bg-gray-500 hover:bg-gray-600 text-white',
    };
    return `${base} ${colors[this.color]}`;
  }
}
