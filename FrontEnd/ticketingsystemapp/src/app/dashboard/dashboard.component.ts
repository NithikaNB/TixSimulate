import { Component, OnInit } from '@angular/core';
import { LogService } from '../services/log-display/log-display.service';
import { TicketPoolService } from '../services/ticket-pool/ticket-pool.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  logs: string[] = [];
  availableTicketCount: number = 0;

  constructor(
    private logService: LogService,
    private ticketPoolService: TicketPoolService
  ) {}

  ngOnInit(): void {
    // Fetch logs
    this.logService.getLogs().subscribe((logMessages) => {
      this.logs = logMessages;
    });

    // Fetch available ticket count (e.g., for "movie" ticket pool)
    this.ticketPoolService.fetchAvailableTickets('movie');

    // Subscribe to real-time ticket updates
    this.ticketPoolService.getAvailableTicketCount().subscribe(ticketCount => {
      this.availableTicketCount = ticketCount;
    });

    // Start real-time updates for available ticket count
    this.ticketPoolService.startRealTimeUpdates('movie');
  }
}
