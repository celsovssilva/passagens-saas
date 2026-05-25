import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PassageiroService } from '../../service/passageiro.service';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-area-passageiro',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './area-passageiro.html',
  styleUrl: './area-passageiro.css'
})
export class AreaPassageiroComponent implements OnInit {
  busca = { origem: '', destino: '' };
  rotasEncontradas: any[] = [];
  viagemSelecionada: any = null;
  historicoCompras: any[] = [];

  userId: number = 0; // Capturado dinamicamente do Token real

  constructor(
    private passageiroService: PassageiroService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    // Recupera o ID do usuário logado decodificando o token atual
    const token = this.authService.getToken();
    if (token) {
      try {
        const base64Url = token.split('.')[1];
        const jsonPayload = decodeURIComponent(window.atob(base64Url.replace(/-/g, '+').replace(/_/g, '/')).split('').map(c => {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        const tokenDecodificado = JSON.parse(jsonPayload);
        this.userId = tokenDecodificado.id || 0; // Certifique-se que seu JWT retorna o "id" do usuário

        this.carregarHistorico();
      } catch (e) {
        console.error('Erro ao ler ID do token', e);
      }
    }
  }

  pesquisarRotas() {
    if (!this.busca.origem || !this.busca.destino) {
      alert('Por favor, preencha a origem e o destino! 🗺️');
      return;
    }

    // Puxa as rotas reais cadastradas no seu banco de dados
    this.passageiroService.pesquisarViagens(this.busca.origem, this.busca.destino).subscribe({
      next: (viagens) => {
        this.rotasEncontradas = viagens;
        if (this.rotasEncontradas.length === 0) {
          alert('Nenhuma viagem encontrada para essa rota no momento. 🎫');
        }
      },
      error: (err) => {
        console.error('Erro ao buscar viagens do banco:', err);
        alert('Erro ao conectar com o servidor para buscar rotas.');
      }
    });
  }

  selecionarViagem(viagem: any) {
    this.viagemSelecionada = viagem;
  }

  finalizarCompra() {
    const dadosCompra = {
      viagemId: this.viagemSelecionada.id,
      passageiroId: this.userId
    };

    // Salva a venda de verdade no banco através do endpoint do Spring
    this.passageiroService.comprarPassagem(dadosCompra).subscribe({
      next: (resposta) => {
        alert('Passagem confirmada e salva no banco de dados! 🎟️✈️');
        this.viagemSelecionada = null;
        this.rotasEncontradas = [];
        this.busca = { origem: '', destino: '' };
        this.carregarHistorico(); // Atualiza a lista de passagens compradas
      },
      error: (err) => {
        console.error('Erro ao processar venda no backend:', err);
        alert('Não foi possível finalizar a compra. Verifique o saldo ou vagas.');
      }
    });
  }

  carregarHistorico() {
    if (this.userId > 0) {
      this.passageiroService.buscarHistoricoCompras(this.userId).subscribe({
        next: (compras) => this.historicoCompras = compras,
        error: (err) => console.error('Erro ao carregar histórico do banco', err)
      });
    }
  }
}
