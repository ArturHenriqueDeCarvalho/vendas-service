#!/bin/bash

echo "🚀 Iniciando a criação de 10 pedidos de teste..."

for i in {1..10}
do
   # Gera um valor aleatório entre 50 e 500
   VALOR=$((50 + RANDOM % 450)).99
   
   curl -s -X POST http://localhost:8080/v1/orders \
   -H "Content-Type: application/json" \
   -d '{
         "customerName": "Cliente de Teste '$i'",
         "totalValue": '$VALOR'
       }' | jq .
       
   echo -e "\n✅ Pedido $i criado!"
   sleep 0.5
done

echo "🎉 Foram criados 10 pedidos com sucesso! Vá ao Swagger ou no endpoint GET /v1/orders para visualizá-los."
