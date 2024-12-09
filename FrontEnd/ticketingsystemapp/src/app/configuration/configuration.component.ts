import { Component } from '@angular/core';
import { ConfigurationService } from '../services/configuration.service';
import { Configuration } from '../models/configuration.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-configuration',
  standalone: true, // Mark as a standalone component
  imports: [FormsModule, CommonModule],
  templateUrl: './configuration.component.html',
  styleUrl: './configuration.component.css'
})

export class ConfigurationComponent {
  constructor(
    private configService: ConfigurationService,
    private snackBar: MatSnackBar) {}

  onSubmit(form: any): void {
    const config: Configuration = {
        totalTickets: form.value.totalTickets,
        ticketReleaseRate: form.value.ticketReleaseRate,
        customerRetrievalRate: form.value.customerRetrievalRate,
        maxTicketCapacity: form.value.maxTicketCapacity
    };

    this.configService.postConfiguration(config).subscribe({
      next: (response: any) => {
        if (response.status === 0) {
          this.snackBar.open('Configuration saved successfully!', 'Close', {
            duration: 3000,
            panelClass: ['snackbar-success']
        });
        console.log('Configuration saved successfully:', response);
      } else {
          this.snackBar.open(`Error: ${response.message}`, 'Close', {
            duration: 5000,
            panelClass: ['snackbar-error']
        });
        console.error('Error from backend:', response.message);
      }

      },
      error: (error) => {
          this.snackBar.open('An unexpected error occurred!', 'Close', {
            duration: 5000,
            panelClass: ['snackbar-error']
      });
      }
  });
}
}
