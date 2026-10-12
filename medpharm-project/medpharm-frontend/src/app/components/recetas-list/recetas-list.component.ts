import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { RecetaService } from '../../services/receta.service';
import { FiltroEstado, Receta } from '../../models/receta.model';

@Component({
  selector: 'app-recetas-list',
  imports: [RouterLink, DatePipe],
  templateUrl: './recetas-list.component.html',
  styleUrl: './recetas-list.component.css'
})
export class RecetasListComponent implements OnInit {
  private recetaService = inject(RecetaService);

  readonly filtros: FiltroEstado[] = ['TODAS', 'PENDIENTE', 'DESPACHADA', 'CANCELADA'];

  recetas = signal<Receta[]>([]);
  filtro = signal<FiltroEstado>('TODAS');
  cargando = signal(false);
  error = signal<string | null>(null);
  procesandoId = signal<number | null>(null);

  recetasFiltradas = computed(() => {
    const estado = this.filtro();
    const lista = this.recetas();
    return estado === 'TODAS' ? lista : lista.filter(r => r.estado === estado);
  });

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando.set(true);
    this.error.set(null);

    this.recetaService.getAll().subscribe({
      next: datos => {
        this.recetas.set(datos);
        this.cargando.set(false);
      },
      error: err => {
        this.error.set(this.mensajeError(err));
        this.cargando.set(false);
      }
    });
  }

  cambiarEstado(receta: Receta, nuevoEstado: 'DESPACHADA' | 'CANCELADA'): void {
    const accion = nuevoEstado === 'DESPACHADA' ? 'despachar' : 'cancelar';
    if (!confirm(`¿Desea ${accion} la receta ${receta.codigoReceta}?`)) {
      return;
    }

    this.procesandoId.set(receta.id);
    this.error.set(null);

    this.recetaService.actualizarEstado(receta.id, nuevoEstado).subscribe({
      next: () => {
        this.recetas.update(lista =>
          lista.map(r => (r.id === receta.id ? { ...r, estado: nuevoEstado } : r))
        );
        this.procesandoId.set(null);
      },
      error: err => {
        this.error.set(this.mensajeError(err));
        this.procesandoId.set(null);
      }
    });
  }

  claseEstado(estado: string): string {
    return `badge badge-${estado.toLowerCase()}`;
  }

  private mensajeError(err: HttpErrorResponse): string {
    if (err.status === 0) return 'No se pudo conectar con el servidor';
    if (err.status === 401 || err.status === 403) return 'No tiene autorización para esta acción';
    return err.error?.detail ?? err.error?.message ?? 'Ocurrió un error inesperado';
  }
}