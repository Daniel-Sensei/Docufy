import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NessunComponenteComponent } from './nessun-componente.component';

describe('NessunComponenteComponent', () => {
  let component: NessunComponenteComponent;
  let fixture: ComponentFixture<NessunComponenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NessunComponenteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NessunComponenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
