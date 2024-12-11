import { TestBed } from '@angular/core/testing';

import { LogDisplayService } from './log-display.service';

describe('LogDisplayService', () => {
  let service: LogDisplayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LogDisplayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
