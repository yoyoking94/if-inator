import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeMembre } from './liste-membre';

describe('ListeMembre', () => {
  let component: ListeMembre;
  let fixture: ComponentFixture<ListeMembre>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListeMembre],
    }).compileComponents();

    fixture = TestBed.createComponent(ListeMembre);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
