import { TestBed } from '@angular/core/testing';

import { StartStopButtonService } from './start-stop-button.service';

describe('StartStopButtonService', () => {
  let service: StartStopButtonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StartStopButtonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
