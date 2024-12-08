import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { ConfigurationComponent } from './app/configuration/configuration.component';

bootstrapApplication(ConfigurationComponent,{
  providers: [
      provideRouter([
          { path: '', component: ConfigurationComponent }, // Default route
          { path: 'configuration', component: ConfigurationComponent }
      ]),
      provideHttpClient()
  ]
}).catch(err => console.error(err));
