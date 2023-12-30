import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { SalesCardComponent } from './dashboard_folder/sales-card/sales-card.component';
import { RevenueCardComponent } from './dashboard_folder/revenue-card/revenue-card.component';
import { CustomersCardComponent } from './dashboard_folder/customers-card/customers-card.component';
import { ReportsComponent } from './dashboard_folder/reports/reports.component';
import { RecentSalesComponent } from './dashboard_folder/recent-sales/recent-sales.component';
import { TopSellingComponent } from './dashboard_folder/top-selling/top-selling.component';
import { RecentActivityComponent } from './dashboard_folder/recent-activity/recent-activity.component';
import { BudgetReportComponent } from './dashboard_folder/budget-report/budget-report.component';
import { WebsiteTrafficComponent } from './dashboard_folder/website-traffic/website-traffic.component';
import { NewsUpdateComponent } from './dashboard_folder/news-update/news-update.component';
import { FooterComponent } from './footer/footer.component';
import { DashboardComponent } from './dashboard_folder/dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ContactComponent } from './contact/contact.component';
import { QuestionComponent } from './question/question.component';
import { ProfileComponent } from './profile/profile.component';
import { CorsiComponent } from './corsi/corsi.component';
import { DettaglioCorsoComponent } from './dettaglio-corso/dettaglio-corso.component';
import { DipendentiComponent } from './dipendenti/dipendenti.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatSortModule } from '@angular/material/sort';
import { DipendentiDettagliComponent } from './dipendenti-dettagli/dipendenti-dettagli.component';
import { DocumentiComponent } from './documenti/documenti.component';
import { DocumentiTabellaComponent } from './documenti-tabella/documenti-tabella.component';
import { AddDocumentModalComponent } from './add-documento-modal/add-document-modal.component';
import { NgbDate, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbDatepickerConfig, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateITParserFormatter } from './NgbDateParserFormatter';
import { ToastrModule } from 'ngx-toastr';
import { AddDipendenteModalComponent } from './add-dipendente-modal/add-dipendente-modal.component';
import { DipendentiTabellaComponent } from './dipendenti-tabella/dipendenti-tabella.component';

import { DoughnutChartDirective } from './doughnut-chart.directives';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SidebarComponent,
    SalesCardComponent,
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
    DoughnutChartDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSortModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    NgbDatepickerConfig,
    { provide: NgbDateParserFormatter, useClass: NgbDateITParserFormatter}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
