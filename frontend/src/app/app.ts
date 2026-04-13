import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ListeMembre } from './pages/liste-membre/liste-membre';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ListeMembre],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
