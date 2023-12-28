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
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
