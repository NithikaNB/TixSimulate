import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StartStopButtonService {

  private apiUrl = 'http://localhost:8080/api/configuration/run-task';

  constructor(private http: HttpClient, private snackBar: MatSnackBar) {}

  runTask(): Observable<any> {
    // Provide an empty object as the body
    return this.http.post<any>(this.apiUrl, {});
  }
  handleResponse(response: any, isRunning: boolean): boolean {
    if (response.status === 0) {
      this.snackBar.open(response.message, 'Close', { duration: 3000 });
      return !isRunning; // Toggle the state
    } else {
      this.snackBar.open('Failed to start/stop task.', 'Close', { duration: 3000 });
      return isRunning; // Keep the state as it is
    }
  }
}
