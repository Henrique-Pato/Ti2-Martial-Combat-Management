document.getElementById('cadastroForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio do formulário padrão
  
    // Obtém os valores dos campos
    var nome = document.getElementById('nome_cad').value;
    var email = document.getElementById('email_cad').value;
    var senha = document.getElementById('senha_cad').value;

    // Cria um objeto com os dados do usuário
    var userData = {
        nome_cad: nome_cad,
        email_cad: email_cad,
        senha_cad: senha_cad
    };
  
    console.log(userData);

    // Faz a chamada de API para o backend
    fetch('/cadastro', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userData)
    })
    .then(function(response) {
      if (response.ok) {
        // Cadastro bem-sucedido, faça o que for necessário (por exemplo, exibir uma mensagem de sucesso)
        console.log('Cadastro realizado com sucesso!');
      } else {
        // Tratar erros de acordo com a resposta do backend
        console.error('Erro durante o cadastro.');
      }
    })
    .catch(function(error) {
      // Tratar erros de conexão ou outros erros
      console.error('Erro na chamada de API:', error);
    });
  });