import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // URL base do backend
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // 1. Rota para Cadastrar Passageiro 🏃‍♂️
  cadastrarPassageiro(dados: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/passageiro/cadastrar`, dados);
  }

  // 2. Rota para Cadastrar Empresa 🏢
  cadastrarEmpresa(dados: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/empresa/cadastrar`, dados);
  }

  // 3. Rota para Login 🔑
  login(credenciais: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, credenciais);
  }
}
