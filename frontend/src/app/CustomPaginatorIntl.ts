import { MatPaginatorIntl } from '@angular/material/paginator';
import { Injectable } from '@angular/core';

@Injectable()
export class CustomPaginatorIntl extends MatPaginatorIntl {
    override itemsPerPageLabel = 'Elementi per pagina:';
    override nextPageLabel = 'Pagina successiva';
    override previousPageLabel = 'Pagina precedente';
    override firstPageLabel = 'Prima pagina';
    override lastPageLabel = 'Ultima pagina';

    override getRangeLabel = (page: number, pageSize: number, length: number) => {
        if (length === 0 || pageSize === 0) {
            return `0 di ${length}`;
        }
        length = Math.max(length, 0);
        const startIndex = page * pageSize;
        const endIndex = startIndex < length ? Math.min(startIndex + pageSize, length) : startIndex + pageSize;
        return `${startIndex + 1} - ${endIndex} di ${length}`;
    };
}
