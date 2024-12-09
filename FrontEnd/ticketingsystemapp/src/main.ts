import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { ConfigurationComponent } from './app/configuration/configuration.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

bootstrapApplication(ConfigurationComponent,{
  providers: [
      provideRouter([
          { path: '', component: ConfigurationComponent }, // Default route
          { path: 'configuration', component: ConfigurationComponent }
      ]),
      provideHttpClient(),
      MatSnackBarModule, provideAnimationsAsync()
  ]
}).catch(err => console.error(err));
