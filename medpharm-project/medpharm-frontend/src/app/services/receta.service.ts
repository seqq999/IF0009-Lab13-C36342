import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Receta, RecetaRequest } from '../models/receta.model';

@Injectable({ providedIn: 'root' })
export class RecetaService {
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8080/api/v1/recetas';

    getAll(): Observable<Receta[]> {
        return this.http.get<Receta[]>(this.apiUrl);
    }

    getByEstado(estado: string): Observable<Receta[]> {
        return this.http.get<Receta[]>(`${this.apiUrl}/estado/${estado}`);
    }

    crear(receta: RecetaRequest): Observable<Receta> {
        return this.http.post<Receta>(this.apiUrl, receta);
    }

    actualizarEstado(id: number, estado: 'DESPACHADA' | 'CANCELADA'): Observable<Receta> {
        return this.http.patch<Receta>(`${this.apiUrl}/${id}/estado`, { estado });
    }
}