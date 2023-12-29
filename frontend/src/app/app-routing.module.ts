import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard_folder/dashboard/dashboard.component';
import { RegisterComponent } from './register/register.component';
import { ContactComponent } from './contact/contact.component';
import { QuestionComponent } from './question/question.component';
import { ProfileComponent } from './profile/profile.component';
import { CorsiComponent } from './corsi/corsi.component';
import { DettaglioCorsoComponent } from './dettaglio-corso/dettaglio-corso.component';

import { DipendentiComponent } from './dipendenti/dipendenti.component';
import { DipendentiDettagliComponent } from './dipendenti-dettagli/dipendenti-dettagli.component';

const routes: Routes = [
  { path: '', component: DashboardComponent },
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
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
