import { Component, OnInit, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RecetaService } from '../../services/receta.service';
import { MedicamentoService } from '../../services/medicamento.service';
import { Medicamento } from '../../models/medicamento.model';
import { RecetaRequest } from '../../models/receta.model';
import { positivoValidator } from '../../validators/positivo.validator';

@Component({
  selector: 'app-receta-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './receta-form.component.html',
  styleUrl: './receta-form.component.css'
})
export class RecetaFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private recetaService = inject(RecetaService);
  private medicamentoService = inject(MedicamentoService);
  private router = inject(Router);

  medicamentos = signal<Medicamento[]>([]);
  guardando = signal(false);
  error = signal<string | null>(null);

  form = this.fb.group({
    pacienteNombre: ['', [Validators.required, Validators.minLength(5)]],
    detalles: this.fb.array([this.crearDetalle()])
  });

  get paciente() { return this.form.controls.pacienteNombre; }
  get detalles() { return this.form.controls.detalles; }

  ngOnInit(): void {
    this.medicamentoService.getAll().subscribe({
      next: datos => this.medicamentos.set(datos),
      error: err => this.error.set(this.mensajeError(err))
    });
  }

  private crearDetalle() {
    return this.fb.group({
      medicamentoId: new FormControl<number | null>(null, Validators.required),
      cantidad: new FormControl<number | null>(1, [Validators.required, positivoValidator]),
      dosisIndicada: ['', Validators.required]
    });
  }

  agregarDetalle(): void {
    this.detalles.push(this.crearDetalle());
  }

  quitarDetalle(indice: number): void {
    if (this.detalles.length > 1) {
      this.detalles.removeAt(indice);
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const valor = this.form.getRawValue();
    const payload: RecetaRequest = {
      pacienteNombre: valor.pacienteNombre!.trim(),
      detalles: valor.detalles.map(d => ({
        medicamentoId: d.medicamentoId!,
        cantidad: Number(d.cantidad),
        dosisIndicada: d.dosisIndicada!.trim()
      }))
    };

    this.guardando.set(true);
    this.error.set(null);

    this.recetaService.crear(payload).subscribe({
      next: () => this.router.navigate(['/recetas']),
      error: err => {
        this.guardando.set(false);
        this.error.set(this.mensajeError(err));
      }
    });
  }

  private mensajeError(err: HttpErrorResponse): string {
    if (err.status === 0) return 'No se pudo conectar con el servidor';
    if (err.status === 401 || err.status === 403) return 'No tiene autorización para esta acción';
    return err.error?.detail ?? err.error?.message ?? 'Ocurrió un error inesperado';
  }
}