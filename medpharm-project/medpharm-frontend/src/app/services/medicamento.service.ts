import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Medicamento } from '../models/medicamento.model';

@Injectable({ providedIn: 'root' })
export class MedicamentoService {
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8080/api/v1/medicamentos';

    getAll(): Observable<Medicamento[]> {
        return this.http.get<Medicamento[]>(this.apiUrl);
    }
}