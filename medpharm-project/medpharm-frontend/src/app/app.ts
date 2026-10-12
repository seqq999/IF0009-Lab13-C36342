import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    @if (auth.isLoggedIn()) {
      <nav class="navbar">
        <span class="marca">MedPharm Express</span>
        <div class="enlaces">
          <a routerLink="/recetas" routerLinkActive="activo">Recetas</a>
          <a routerLink="/nueva-receta" routerLinkActive="activo">Nueva receta</a>
        </div>
        <div class="usuario">
          <span>{{ auth.username() }} ({{ auth.rol() }})</span>
          <button type="button" class="btn btn-secundario btn-sm" (click)="auth.logout()">Salir</button>
        </div>
      </nav>
    }
    <router-outlet />
  `,
  styles: [`
    .navbar { display: flex; align-items: center; gap: 1.5rem; padding: .75rem 1.5rem;
              background: #1f4e78; color: #fff; flex-wrap: wrap; }
    .marca { font-weight: 700; font-size: 1.1rem; }
    .enlaces { display: flex; gap: 1rem; flex: 1; }
    .enlaces a { color: #cfe0f1; text-decoration: none; padding: .25rem .5rem; border-radius: 4px; }
    .enlaces a.activo, .enlaces a:hover { background: rgba(255,255,255,.15); color: #fff; }
    .usuario { display: flex; align-items: center; gap: .75rem; font-size: .9rem; }
  `]
})
export class App {
  auth = inject(AuthService);
}