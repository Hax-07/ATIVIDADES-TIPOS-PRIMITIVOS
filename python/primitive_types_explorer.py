"""
EXPLORADOR INTERATIVO DE TIPOS PRIMITIVOS - PYTHON
------------------------------------------------------
Aplicação de console com menu, onde o usuário escolhe um tipo
e digita valores para ver análises, conversões e curiosidades
sobre os tipos built-in do Python.

Executar:
    python3 primitive_types_explorer.py
"""

import sys


def exibir_banner():
    print("=====================================================")
    print("  EXPLORADOR INTERATIVO DE TIPOS PRIMITIVOS - PYTHON")
    print("=====================================================")


def exibir_menu():
    print("\nEscolha uma opção:")
    print("1 - Explorar int (precisão arbitrária)")
    print("2 - Explorar float")
    print("3 - Explorar complex")
    print("4 - Explorar bool")
    print("5 - Explorar str")
    print("6 - Explorar bytes")
    print("7 - Ver tamanho em memória (sys.getsizeof)")
    print("8 - Demonstrar conversões (casting)")
    print("0 - Sair")


def explorar_int():
    entrada = input("\nDigite um número inteiro (pode ser gigante!): ").strip()
    try:
        valor = int(entrada)
    except ValueError:
        print(">> Valor inválido.")
        return
    print("\n--- Análise do int ---")
    print(f"Valor: {valor}")
    print(f"Quantidade de dígitos: {len(str(abs(valor)))}")
    print(f"É par? {valor % 2 == 0}")
    print(f"Tamanho em memória: {sys.getsizeof(valor)} bytes")
    print(">> Lembrete: Python não tem overflow em int, cresce sob demanda.")


def explorar_float():
    entrada = input("\nDigite um número decimal: ").strip()
    try:
        valor = float(entrada)
    except ValueError:
        print(">> Valor inválido.")
        return
    print("\n--- Análise do float ---")
    print(f"Valor: {valor}")
    print(f"É inteiro (mesmo sendo float)? {valor.is_integer()}")
    print(f"Arredondado (2 casas): {round(valor, 2)}")
    print(f"Como fração: {valor.as_integer_ratio()}")


def explorar_complex():
    print("\n--- complex ---")
    real = input("Parte real: ").strip()
    imag = input("Parte imaginária: ").strip()
    try:
        valor = complex(float(real), float(imag))
    except ValueError:
        print(">> Valores inválidos.")
        return
    print(f"\nNúmero complexo: {valor}")
    print(f"Parte real: {valor.real}")
    print(f"Parte imaginária: {valor.imag}")
    print(f"Conjugado: {valor.conjugate()}")
    print(f"Módulo (abs): {abs(valor):.4f}")


def explorar_bool():
    entrada = input("\nDigite True ou False: ").strip().lower()
    valor = entrada == "true"
    print("\n--- Análise do bool ---")
    print(f"Valor: {valor}")
    print(f"bool é subtipo de int: True == 1 -> {True == 1}, False == 0 -> {False == 0}")
    print(f"not {valor} = {not valor}")
    print(f"Curiosidade: bool([]) = {bool([])} | bool([1,2]) = {bool([1, 2])} | bool('') = {bool('')}")


def explorar_str():
    entrada = input("\nDigite um texto: ")
    print("\n--- Análise da str ---")
    print(f'Valor: "{entrada}"')
    print(f"Tamanho: {len(entrada)}")
    print(f"Maiúsculo: {entrada.upper()}")
    print(f"Invertido: {entrada[::-1]}")
    print(f"É numérico? {entrada.isdigit()}")
    print(f"Tamanho em memória: {sys.getsizeof(entrada)} bytes")


def explorar_bytes():
    entrada = input("\nDigite um texto para converter em bytes: ")
    valor = entrada.encode("utf-8")
    print("\n--- Análise de bytes ---")
    print(f"Valor original (str): {entrada}")
    print(f"Como bytes: {valor}")
    print(f"Tamanho em bytes: {len(valor)}")
    print(f"Decodificado de volta: {valor.decode('utf-8')}")


def ver_tamanho_memoria():
    print("\n--- Tamanho em memória de valores comuns ---")
    exemplos = {
        "int pequeno (0)": 0,
        "int médio (1000)": 1000,
        "float (3.14)": 3.14,
        "bool (True)": True,
        "str curta ('a')": "a",
        "str longa (50 chars)": "a" * 50,
        "None": None,
    }
    for nome, valor in exemplos.items():
        print(f"{nome:<22} -> {sys.getsizeof(valor)} bytes")


def demonstrar_conversoes():
    print("\n--- Demonstração de conversões (casting) ---")
    texto = "123"
    numero = int(texto)
    print(f'int("{texto}")        = {numero} ({type(numero).__name__})')

    numero_float = float(numero)
    print(f"float({numero})           = {numero_float} ({type(numero_float).__name__})")

    de_volta_str = str(numero_float)
    print(f'str({numero_float})       = "{de_volta_str}" ({type(de_volta_str).__name__})')

    print(f"bool(0)              = {bool(0)}")
    print(f"bool(1)              = {bool(1)}")
    print(f'bool("")             = {bool("")}')
    print(f'bool("qualquer")     = {bool("qualquer")}')

    print(f'int("abc") geraria ValueError -> tentando de forma segura:')
    try:
        int("abc")
    except ValueError as e:
        print(f"   Erro capturado: {e}")


def main():
    exibir_banner()
    rodando = True

    opcoes = {
        "1": explorar_int,
        "2": explorar_float,
        "3": explorar_complex,
        "4": explorar_bool,
        "5": explorar_str,
        "6": explorar_bytes,
        "7": ver_tamanho_memoria,
        "8": demonstrar_conversoes,
    }

    while rodando:
        exibir_menu()
        opcao = input("> ").strip()

        if opcao == "0":
            print("\nEncerrando o explorador. Até mais!")
            rodando = False
        elif opcao in opcoes:
            opcoes[opcao]()
        else:
            print("\n>> Opção inválida. Tente novamente.")


if __name__ == "__main__":
    main()
