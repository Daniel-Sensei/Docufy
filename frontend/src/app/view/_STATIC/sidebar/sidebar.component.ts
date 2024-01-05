import { Component } from '@angular/core';
import { AuthService } from '../../../service/auth/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  role: string = 'C'

  constructor(private auth: AuthService) { }

  logout() {
    this.auth.logout();
  }
}
