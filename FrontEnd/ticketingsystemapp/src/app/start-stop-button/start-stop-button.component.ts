import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { StartStopButtonService } from '../services/start-stop-button/start-stop-button.service';

@Component({
  selector: 'app-start-stop-button',
  imports: [CommonModule],
  templateUrl: './start-stop-button.component.html',
  styleUrls: ['./start-stop-button.component.css']
})
export class StartStopButtonComponent {
  isRunning: boolean = false; // Default state is "off"

  constructor(private startStopButtonService: StartStopButtonService, private snackBar: MatSnackBar) {}

  toggleTask(): void {
    const action = this.isRunning ? 'Stop' : 'Start';
    this.startStopButtonService.runTask().subscribe({
      next: (response: any) => {
        if (response.status === 0) {
          this.isRunning = !this.isRunning; // Toggle state
          this.snackBar.open(response.message, 'Close', { duration: 3000 });
        }
      },
      error: (err) => {
        console.error('Error running task:', err);
        this.snackBar.open('Failed to execute task.', 'Close', { duration: 3000 });
      }
    });
  }
}
