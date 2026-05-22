import { Routes } from '@angular/router';
import { LandingPageComponent } from './components/landing-page/landing-page';
import { LoginComponent } from './components/login/login'; // 👈 Mudado de Login para LoginComponent
import { CadastroComponent } from './components/cadastro/cadastro'; // 👈 Mudado de Cadastro para CadastroComponent

export const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'login', component: LoginComponent },       // 👈 Usando LoginComponent aqui
  { path: 'cadastro', component: CadastroComponent }  // 👈 Usando CadastroComponent aqui
];
