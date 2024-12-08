import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Configuration } from '../models/configuration.model';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {
  private apiUrl = 'http://localhost:8080/api/configuration/create-config'; // Spring Boot API endpoint

  constructor(private http: HttpClient) { }

  postConfiguration(config: Configuration): Observable<any> {
    return this.http.post(this.apiUrl, config);
}
}
