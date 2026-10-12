export type EstadoReceta = 'PENDIENTE' | 'DESPACHADA' | 'CANCELADA';
export type FiltroEstado = 'TODAS' | EstadoReceta;

export interface DetalleReceta {
    id: number;
    medicamentoId: number;
    medicamentoCodigo: string;
    medicamentoNombre: string;
    precioUnitario: number;
    cantidad: number;
    dosisIndicada: string;
}

export interface Receta {
    id: number;
    codigoReceta: string;
    pacienteNombre: string;
    medicoId: number;
    medicoNombre: string;
    estado: EstadoReceta;
    fechaEmision: string;
    detalles: DetalleReceta[];
}

export interface DetalleRecetaRequest {
    medicamentoId: number;
    cantidad: number;
    dosisIndicada: string;
}

export interface RecetaRequest {
    pacienteNombre: string;
    detalles: DetalleRecetaRequest[];
}