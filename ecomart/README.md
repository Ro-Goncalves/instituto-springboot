Temperature
Definição: O parâmetro Temperature controla a aleatoriedade das respostas geradas pelo modelo.
Intervalo de valores: 0.0 a 2.0
Descrição: Valores mais baixos, como 0.5 ou 0.6, fazem com que o modelo produza respostas mais determinísticas e focadas, reduzindo a criatividade. Já valores mais altos, como 1.2 ou 1.5, aumentam a criatividade e a diversidade das respostas, mas também podem introduzir mais incoerência.
Uso: Use um valor baixo para tarefas que requerem respostas precisas e coerentes, como explicações técnicas. Use temperature alta para tarefas criativas, como escrita de histórias.
Max Tokens
Definição: O parâmetro Maximum Tokens define o número máximo de tokens (palavras ou partes de palavras) na resposta gerada pelo modelo.
Intervalo: Depende do modelo utilizado. Normalmente tem um limite de 4096 ou 16384.
Descrição: Este parâmetro limita o comprimento da resposta. Por exemplo, se for definido como 50, a resposta não ultrapassará 50 tokens.
Uso: Ajuste de acordo com a necessidade da aplicação. Para respostas curtas e diretas, use um valor menor. Para respostas detalhadas, use um valor maior.
TopP
Definição: O parâmetro TopP, também conhecido como núcleo de amostragem, controla a diversidade através da restrição do conjunto de candidatos para a próxima palavra.
Intervalo: 0.0 a 1.0
Descrição: Com TopP de 0.9, o modelo considera apenas os 90% dos candidatos mais prováveis (com base na sua probabilidade cumulativa) para a próxima palavra. Isso pode tornar as respostas mais variadas e menos previsíveis.
Uso: Use em conjunto com temperature para ajustar a criatividade e diversidade das respostas. Valores mais altos podem gerar respostas mais variadas.
Frequency penalty
Definição: O parâmetro Frequency penalty aplica uma penalidade a palavras que já apareceram na resposta.
Intervalo: 0.0 a 2.0
Descrição: Valores positivos reduzem a probabilidade de repetir as mesmas palavras, incentivando a diversidade lexical. Valores negativos podem aumentar a probabilidade de repetição.
Uso: Utilize um valor alto quando quiser respostas com menor repetição de palavras, sendo útil em situações que requerem maior variação lexical.
Presence penalty
Definição: O parâmetro Presence penalty penaliza a presença de determinadas palavras na resposta.
Intervalo: 0.0 a 2.0
Descrição: Similar ao Frequency penalty mas com foco na presença de palavras, não apenas na frequência. Valores altos desencorajam a inclusão de palavras presentes em contextos anteriores, enquanto valores baixos incentivam essa inclusão.
Uso: Ajuste para controlar a inclusão de certas palavras na resposta, mantendo ou alterando o contexto de forma mais flexível.
Stop sequences
Definição: O parâmetro Stop sequences define uma ou mais sequências de caracteres que, ao serem encontradas, fazem com que o modelo interrompa a geração de texto.
Descrição: Pode ser uma string única ou uma lista de strings. Quando uma dessas sequências aparece na resposta gerada, a geração de texto é interrompida.
Uso: Use para delimitar respostas e evitar saídas excessivamente longas ou fora de contexto. Por exemplo, em um chatbot, você pode definir sequências de parada para garantir que o modelo responda de maneira concisa.

https://platform.openai.com/docs/guides/prompt-engineering