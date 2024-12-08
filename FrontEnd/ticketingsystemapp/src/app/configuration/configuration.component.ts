import { Component } from '@angular/core';
import { ConfigurationService } from '../services/configuration.service';
import { Configuration } from '../models/configuration.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-configuration',
  standalone: true, // Mark as a standalone component
  imports: [FormsModule, CommonModule],
  templateUrl: './configuration.component.html',
  styleUrl: './configuration.component.css'
})

export class ConfigurationComponent {
  constructor(private configService: ConfigurationService) {}

  onSubmit(form: any): void {
    const config: Configuration = {
        maximumTicketCapacity: form.value.maximumTicketCapacity,
        totalNumberOfTickets: form.value.totalNumberOfTickets,
        ticketReleaseRate: form.value.ticketReleaseRate,
        customerRetrievalRate: form.value.customerRetrievalRate
    };

    this.configService.postConfiguration(config).subscribe({
      next: (response) => {
        if (response.status === 0) {
          console.log('Configuration saved successfully:', response);
          alert('Configuration saved successfully!');
      } else {
          console.error('Error from backend:', response.message);
          alert(`Error: ${response.message}`);
      }

      },
      error: (error) => {
          console.error('Error saving configuration:', error);
          alert('An unexpected error occurred while saving the configuration.');
      }
  });
}
}
