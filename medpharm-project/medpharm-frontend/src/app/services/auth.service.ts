import { Injectable, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { AuthResponse, LoginRequest } from '../models/auth.model';

const CLAVE_SESION = 'medpharm_session';

@Injectable({ providedIn: 'root' })
export class AuthService {
    private http = inject(HttpClient);
    private router = inject(Router);
    private apiUrl = 'http://localhost:8080/api/v1/auth';

    private readonly _session = signal<AuthResponse | null>(this.leerSesion());

    readonly session = this._session.asReadonly();
    readonly isLoggedIn = computed(() => !!this._session()?.token);
    readonly username = computed(() => this._session()?.username ?? '');
    readonly rol = computed(() => this._session()?.rol ?? '');

    login(credentials: LoginRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
            tap(respuesta => {
                localStorage.setItem(CLAVE_SESION, JSON.stringify(respuesta));
                this._session.set(respuesta);
            })
        );
    }

    logout(): void {
        localStorage.removeItem(CLAVE_SESION);
        this._session.set(null);
        this.router.navigate(['/login']);
    }

    getToken(): string | null {
        return this._session()?.token ?? null;
    }

    private leerSesion(): AuthResponse | null {
        try {
            const guardada = localStorage.getItem(CLAVE_SESION);
            return guardada ? (JSON.parse(guardada) as AuthResponse) : null;
        } catch {
            return null;
        }
    }
}
