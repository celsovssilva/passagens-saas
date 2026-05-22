import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service'; // Ajuste o caminho de pastas se necessário

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent implements OnInit {

  usuarioLogado: any = { nome: '', role: '', email: '' };
  viagensRecentes: any[] = [];

  // O "public auth" garante que as funções .isAdmin(), .isEmpresa() funcionem no HTML
  constructor(public router: Router, public auth: AuthService) {}

  ngOnInit() {
    const token = this.auth.getToken();

    if (!token) {
      alert('Acesso negado. Por favor, faça o login primeiro! 🔒');
      this.auth.logout();
      return;
    }

    try {
      // 🚀 Decodificação nativa de JWT com JS Puro
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));

      const tokenDecodificado = JSON.parse(jsonPayload);
      console.log('Dados do Token Real:', tokenDecodificado);

      // Extrai o papel (role) do token para sincronizar com o localStorage
      const userRole = tokenDecodificado.role || tokenDecodificado.roles || 'PASSAGEIRO';

      this.usuarioLogado = {
        nome: tokenDecodificado.nome || 'Usuário Autenticado',
        email: tokenDecodificado.sub || tokenDecodificado.email || 'E-mail não encontrado',
        role: String(userRole).toUpperCase()
      };

      // Alinha o role lido do token direto com o localStorage por consistência
      localStorage.setItem('role', this.usuarioLogado.role);

    } catch (error) {
      console.error('Erro ao ler token real:', error);
      this.auth.logout();
    }
  }

  logout() {
    this.auth.logout();
  }
}
