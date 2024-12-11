import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { ConfigurationComponent } from './app/configuration/configuration.component';
import { LogDisplayComponent } from './app/log-display/log-display.component';
import { AppRootComponent } from './app/app-root/app-root.component';
import { DashboardComponent } from './app/dashboard/dashboard.component';

bootstrapApplication(AppRootComponent,{
  providers: [
      provideRouter([
          { path: '', redirectTo: '/configuration', pathMatch: 'full' }, // Default route
          { path: 'configuration', component: ConfigurationComponent },
          { path: 'logs', component: DashboardComponent }
      ]),
      provideHttpClient(),
      MatSnackBarModule,
      provideAnimationsAsync()
  ]
}).catch(err => console.error(err));
