import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './view/_LOGIN/login/login.component';
import { DashboardComponent } from './view/_DASHBOARD/dashboard/dashboard.component';
import { RegisterComponent } from './view/_LOGIN/register/register.component';
import { ContactComponent } from './view/_STATIC/contact/contact.component';
import { QuestionComponent } from './view/_STATIC/question/question.component';
import { ProfileComponent } from './view/_STATIC/profile/profile.component';
import { CorsiComponent } from './view/_CORSI/corsi/corsi.component';
import { DettaglioCorsoComponent } from './view/_CORSI/dettaglio-corso/dettaglio-corso.component';

import { DocumentiComponent } from './view/_DOCUMENTI/documenti/documenti.component';
import { DipendentiComponent } from './view/_DIPENDENTI/dipendenti/dipendenti.component';
import { DipendentiDettagliComponent } from './view/_DIPENDENTI/dipendenti-dettagli/dipendenti-dettagli.component';

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'documenti', component: DocumentiComponent },
  { path: 'dipendenti', component: DipendentiComponent},
  { path: 'dipendenti/:id', component: DipendentiDettagliComponent},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'contact', component: ContactComponent },
  { path: 'question', component: QuestionComponent},
  { path: 'profile', component: ProfileComponent},
  { path : 'corsi', component: CorsiComponent},
  { path: 'dettaglio_corso', component:DettaglioCorsoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})

export class AppRoutingModule { }
