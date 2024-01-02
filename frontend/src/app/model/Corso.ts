export interface Corso{
    id: number;
    nome: string;
    tipo: string; 
    aziendaErogatrice: string;
    costo:number;
    descrizione: string;
    breveDescrizione: string;
    dataInizio: string;
    dataFine: string;
    posti: number;
    postiDisponibili: number;
    esameFinale: boolean;
}