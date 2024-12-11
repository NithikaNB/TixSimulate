import { Injectable } from '@angular/core';
import {Client, Message} from '@stomp/stompjs'
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogService {
  private client: Client;
  private logMessages: BehaviorSubject<string[]> = new BehaviorSubject<string[]>([]);


  constructor() {
    this.client = new Client({
      brokerURL: 'ws://localhost:8080/logs/websocket', // Websocket endpoint
      reconnectDelay: 5000, // Reconnect after 5 seconds
    });
    this.client.onConnect = () => {
      this.client.subscribe('/topic/logs', (message: Message) => {
        const logs = [...this.logMessages.value, message.body];
        this.logMessages.next(logs);
      });
    };

    this.client.activate();
}

getLogs(): Observable<string[]> {
  return this.logMessages.asObservable();
  }
}
