import { Component, OnInit } from '@angular/core';
import { LogService } from '../services/log-display/log-display.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-log-display',
  imports: [CommonModule],
  templateUrl: './log-display.component.html',
  styleUrl: './log-display.component.css'
})
export class LogDisplayComponent implements OnInit{
  logs: string[] = [];

  constructor(private logService: LogService) {}

  ngOnInit(): void {
      this.logService.getLogs().subscribe((logMessages) => {
        console.log('Logs in component:', logMessages); // Debug log
        this.logs = logMessages;
      });
  }

}
