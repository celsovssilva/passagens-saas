import { Component } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  // Objeto idêntico ao 'LoginRequest' do seu record Java
  credenciais = {
    login: '',
    senha: ''
  };

  constructor(private authService: AuthService, private router: Router) {}

  executarLogin() {
    // Validação simples antes de enviar
    if (!this.credenciais.login || !this.credenciais.senha) {
      alert('Por favor, preencha todos os campos.');
      return;
    }

    this.authService.login(this.credenciais).subscribe({
      next: (resposta: any) => {
        alert('Login realizado com sucesso! 🎉');
        console.log('Dados do login/Token retornados pelo Java:', resposta);

        // 💾 Se o seu Java retorna um Token JWT, salvamos ele aqui:
        if (resposta && resposta.token) {
          localStorage.setItem('token', resposta.token);
        }

        // Redireciona para a página principal do sistema após o login
        // this.router.navigate(['/dashboard']);
      },
      error: (erro: any) => {
        console.error('Erro ao tentar logar:', erro);
        alert('Falha no login. Verifique seu usuário/senha ou o console.');
      }
    });
  }
}
