// ticket-pool.service.ts
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, catchError, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TicketPoolService {
  private availableTickets: BehaviorSubject<number> = new BehaviorSubject<number>(0)
  constructor(private http: HttpClient) {}

  getAvailableTickets(ticketPoolName: string): Observable<number> {
    return this.http.get<number>(`http://localhost:8080/api/ticket-pools/availabletickets/${ticketPoolName}`).pipe(
      catchError((error) => {
        console.error('Error fetching available tickets:', error);
        return of(0); // Return 0 tickets if pool does not exist
      })
    );
  }

  // Method to fetch and set available tickets initially
  fetchAvailableTickets(ticketPoolName: string): void {
    this.getAvailableTickets(ticketPoolName).subscribe((ticketCount: number) => {
      this.availableTickets.next(ticketCount);
    });
  }

  // Method to subscribe to available ticket count updates
  getAvailableTicketCount(): Observable<number> {
    return this.availableTickets.asObservable();
  }

    // Method to update available tickets in real-time (you can periodically poll or use WebSocket if your backend supports it)
    startRealTimeUpdates(ticketPoolName: string): void {
      setInterval(() => {
        this.fetchAvailableTickets(ticketPoolName);
      }, 100); // Poll every 5 seconds for real-time updates
    }
}
