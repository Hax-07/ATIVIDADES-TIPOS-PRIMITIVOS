const readline = require("readline");

/**
 * EXPLORADOR INTERATIVO DE TIPOS PRIMITIVOS - JAVASCRIPT
 * ---------------------------------------------------------
 * Aplicação de console (Node.js) com menu, onde o usuário
 * escolhe um tipo primitivo, digita um valor, e o programa
 * mostra o typeof, coerções e curiosidades do tipo.
 *
 * Executar:
 *   node primitiveTypesExplorer.js
 */

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

// Fila de linhas: evita perda de entrada quando várias linhas chegam
// de uma vez (comum em pipes/scripts), garantindo que cada pergunta()
// pegue a linha correta na ordem certa.
const filaDeLinhas = [];
let resolverPendente = null;

rl.on("line", (linha) => {
  if (resolverPendente) {
    const resolver = resolverPendente;
    resolverPendente = null;
    resolver(linha);
  } else {
    filaDeLinhas.push(linha);
  }
});

function pergunta(texto) {
  process.stdout.write(texto);
  return new Promise((resolve) => {
    if (filaDeLinhas.length > 0) {
      resolve(filaDeLinhas.shift());
    } else {
      resolverPendente = resolve;
    }
  });
}

function exibirBanner() {
  console.log("=====================================================");
  console.log("  EXPLORADOR INTERATIVO DE TIPOS PRIMITIVOS - JS");
  console.log("=====================================================");
}

function exibirMenu() {
  console.log("\nEscolha uma opção:");
  console.log("1 - Explorar number");
  console.log("2 - Explorar string");
  console.log("3 - Explorar boolean");
  console.log("4 - Explorar bigint");
  console.log("5 - Explorar null / undefined");
  console.log("6 - Demonstrar coerção de tipos (comparações malucas do JS)");
  console.log("7 - Testar seu próprio valor com typeof");
  console.log("0 - Sair");
}

async function explorarNumber() {
  const entrada = await pergunta("\nDigite um número: ");
  const valor = Number(entrada);

  console.log("\n--- Análise do number ---");
  console.log(`Valor: ${valor}`);
  console.log(`typeof: ${typeof valor}`);
  console.log(`É inteiro seguro? ${Number.isSafeInteger(valor)}`);
  console.log(`É NaN? ${Number.isNaN(valor)}`);
  console.log(`Number.MAX_SAFE_INTEGER: ${Number.MAX_SAFE_INTEGER}`);
  if (valor > Number.MAX_SAFE_INTEGER) {
    console.log(">> Cuidado: esse número passou do limite seguro de precisão!");
  }
}

async function explorarString() {
  const entrada = await pergunta("\nDigite um texto: ");
  console.log("\n--- Análise da string ---");
  console.log(`Valor: "${entrada}"`);
  console.log(`typeof: ${typeof entrada}`);
  console.log(`Tamanho (length): ${entrada.length}`);
  console.log(`Maiúsculo: ${entrada.toUpperCase()}`);
  console.log(`Invertido: ${[...entrada].reverse().join("")}`);
  console.log(`Convertido para number: ${Number(entrada)} (NaN se não for número válido)`);
}

async function explorarBoolean() {
  const entrada = (await pergunta("\nDigite true ou false: ")).trim().toLowerCase();
  const valor = entrada === "true";
  console.log("\n--- Análise do boolean ---");
  console.log(`Valor interpretado: ${valor}`);
  console.log(`typeof: ${typeof valor}`);
  console.log(`Negação (!): ${!valor}`);
  console.log(`Coerção de "0" (string não vazia) para boolean: ${Boolean("0")} (curiosidade: string "0" é truthy!)`);
  console.log(`Coerção de 0 (número) para boolean: ${Boolean(0)}`);
}

async function explorarBigInt() {
  console.log("\n--- bigint ---");
  console.log("Usado para números maiores que Number.MAX_SAFE_INTEGER");
  const entrada = await pergunta("Digite um número inteiro grande: ");
  try {
    const valor = BigInt(entrada);
    console.log(`Valor: ${valor}n`);
    console.log(`typeof: ${typeof valor}`);
    console.log(`Somado com 1n: ${valor + 1n}`);
  } catch {
    console.log(">> Valor inválido para bigint.");
  }
}

function explorarNullUndefined() {
  let naoDeclarada;
  const valorNulo = null;
  console.log("\n--- null vs undefined ---");
  console.log(`undefined | typeof: ${typeof naoDeclarada}`);
  console.log(`null      | typeof: ${typeof valorNulo} (bug histórico: deveria ser "null")`);
  console.log(`null == undefined  -> ${null == undefined} (comparação flexível)`);
  console.log(`null === undefined -> ${null === undefined} (comparação estrita)`);
}

function demonstrarCoercao() {
  console.log("\n--- Coerções malucas do JavaScript ---");
  console.log(`"5" + 3        = ${"5" + 3}   (concatenação: string)`);
  console.log(`"5" - 3        = ${"5" - 3}   (coerção para number)`);
  console.log(`[] + []        = "${[] + []}"   (dois arrays viram string vazia)`);
  console.log(`[] + {}        = "${[] + {}}"`);
  console.log(`true + true    = ${true + true}   (booleans viram number)`);
  console.log(`"10" == 10     = ${"10" == 10}   (comparação flexível)`);
  console.log(`"10" === 10    = ${"10" === 10}   (comparação estrita)`);
}

async function testarValorPropio() {
  const entrada = await pergunta("\nDigite qualquer valor para descobrir o tipo: ");
  console.log("\n--- Resultado ---");
  console.log(`Você digitou (sempre chega como string do console): "${entrada}"`);
  console.log(`typeof da entrada bruta: ${typeof entrada}`);

  if (!isNaN(entrada) && entrada.trim() !== "") {
    console.log(`Se convertido com Number(): ${Number(entrada)} (typeof: number)`);
  }
  if (entrada === "true" || entrada === "false") {
    console.log(`Se convertido com Boolean literal: ${entrada === "true"} (typeof: boolean)`);
  }
}

async function main() {
  exibirBanner();
  let rodando = true;

  while (rodando) {
    exibirMenu();
    const opcao = (await pergunta("> ")).trim();

    switch (opcao) {
      case "1": await explorarNumber(); break;
      case "2": await explorarString(); break;
      case "3": await explorarBoolean(); break;
      case "4": await explorarBigInt(); break;
      case "5": explorarNullUndefined(); break;
      case "6": demonstrarCoercao(); break;
      case "7": await testarValorPropio(); break;
      case "0":
        console.log("\nEncerrando o explorador. Até mais!");
        rodando = false;
        break;
      default:
        console.log("\n>> Opção inválida. Tente novamente.");
    }
  }

  rl.close();
}

main();
