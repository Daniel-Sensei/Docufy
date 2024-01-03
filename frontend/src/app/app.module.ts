import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './view/_STATIC/header/header.component';
import { SidebarComponent } from './view/_STATIC/sidebar/sidebar.component';
import { RevenueCardComponent } from './view/_DASHBOARD/revenue-card/revenue-card.component';
import { CustomersCardComponent } from './view/_DASHBOARD/customers-card/customers-card.component';
import { ReportsComponent } from './view/_DASHBOARD/reports/reports.component';
import { RecentSalesComponent } from './view/_DASHBOARD/recent-sales/recent-sales.component';
import { TopSellingComponent } from './view/_DASHBOARD/top-selling/top-selling.component';
import { RecentActivityComponent } from './view/_DASHBOARD/recent-activity/recent-activity.component';
import { BudgetReportComponent } from './view/_DASHBOARD/budget-report/budget-report.component';
import { WebsiteTrafficComponent } from './view/_DASHBOARD/website-traffic/website-traffic.component';
import { NewsUpdateComponent } from './view/_DASHBOARD/news-update/news-update.component';
import { FooterComponent } from './view/_STATIC/footer/footer.component';
import { DashboardComponent } from './view/_DASHBOARD/dashboard/dashboard.component';
import { LoginComponent } from './view/_LOGIN/login/login.component';
import { RegisterComponent } from './view/_LOGIN/register/register.component';
import { ContactComponent } from './view/_STATIC/contact/contact.component';
import { QuestionComponent } from './view/_STATIC/question/question.component';
import { ProfileComponent } from './view/_STATIC/profile/profile.component';
import { CorsiComponent } from './view/_CORSI/corsi/corsi.component';
import { DettaglioCorsoComponent } from './view/_CORSI/dettaglio-corso/dettaglio-corso.component';
import { DipendentiComponent } from './view/_DIPENDENTI/dipendenti/dipendenti.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { DipendentiDettagliComponent } from './view/_DIPENDENTI/dipendenti-dettagli/dipendenti-dettagli.component';
import { DocumentiComponent } from './view/_DOCUMENTI/documenti/documenti.component';
import { DocumentiTabellaComponent } from './view/_DOCUMENTI/documenti-tabella/documenti-tabella.component';
import { AddDocumentModalComponent } from './view/_DOCUMENTI/add-documento-modal/add-document-modal.component';
import { NgbDate, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbDatepickerConfig, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateITParserFormatter } from './NgbDateParserFormatter';
import { ToastrModule } from 'ngx-toastr';
import { AddDipendenteModalComponent } from './view/_DIPENDENTI/add-dipendente-modal/add-dipendente-modal.component';
import { DipendentiTabellaComponent } from './view/_DIPENDENTI/dipendenti-tabella/dipendenti-tabella.component';
import { AddDipendenteCorsoModalComponent } from './view/_CORSI/add-dipendente-corso-modal/add-dipendente-corso-modal.component';


import { DoughnutChartDirective } from './doughnut-chart.directive';
import { BarChartDirective } from './bar-chart-directive';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { SelettoreAziendaComponent } from './view/_DASHBOARD/selettore-azienda/selettore-azienda.component';
import { RiepilogoCorsiComponent } from './view/_DASHBOARD/riepilogo-corsi/riepilogo-corsi.component';
import { CorsiCardComponent } from './view/_CORSI/corsi-card/corsi-card.component';

import { MatPaginatorIntl } from '@angular/material/paginator';
import { CustomPaginatorIntl } from './CustomPaginatorIntl';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SidebarComponent,
    RevenueCardComponent,
    CustomersCardComponent,
    ReportsComponent,
    RecentSalesComponent,
    TopSellingComponent,
    RecentActivityComponent,
    BudgetReportComponent,
    WebsiteTrafficComponent,
    NewsUpdateComponent,
    FooterComponent,
    DashboardComponent,
    LoginComponent,
    RegisterComponent,
    ContactComponent,
    QuestionComponent,
    ProfileComponent,
    CorsiComponent,
    DettaglioCorsoComponent,
    DipendentiComponent,
    DipendentiDettagliComponent,
    DocumentiComponent,
    DocumentiTabellaComponent,
    AddDocumentModalComponent,
    AddDipendenteModalComponent,
    DipendentiTabellaComponent,
    AddDipendenteCorsoModalComponent,
    DoughnutChartDirective,
    BarChartDirective,
    SelettoreAziendaComponent,
    RiepilogoCorsiComponent,
    CorsiCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSortModule,
    MatTableModule,
    MatPaginatorModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule,
    ToastrModule.forRoot(),
    NgMultiSelectDropDownModule.forRoot(),
  ],
  providers: [
    NgbDatepickerConfig,
    { provide: NgbDateParserFormatter, useClass: NgbDateITParserFormatter},
    { provide: MatPaginatorIntl, useClass: CustomPaginatorIntl }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
