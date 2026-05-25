import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class PassageiroService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private authService: AuthService) {}

  // Cria os cabeçalhos com o Token JWT automaticamente para as rotas protegidas
  private getHeaders() {
    const token = this.authService.getToken();
    return {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      })
    };
  }

  // 🔍 Busca viagens/rotas reais baseadas nos parâmetros informados
  pesquisarViagens(origem: string, destino: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/viagem/pesquisar?origem=${origem}&destino=${destino}`,
      this.getHeaders()
    );
  }

  // 🚌 Busca detalhes específicos de um veículo/transporte
  buscarVeiculo(idTransporte: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/transport/buscar/${idTransporte}`, this.getHeaders());
  }

  // 💳 Efetua a compra da passagem salvando no banco de dados
  comprarPassagem(dadosCompra: { viagemId: number, passageiroId: number }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/compra/comprar`, dadosCompra, this.getHeaders());
  }

  // 📊 Busca o histórico de viagens compradas pelo Passageiro
  buscarHistoricoCompras(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/compra/historico/${userId}`, this.getHeaders());
  }
}
