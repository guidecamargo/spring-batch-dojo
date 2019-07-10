package br.com.ciandt.dojo.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import br.com.ciandt.dojo.model.Cliente;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

    private static final String OUTPUT_FILE = "target/output.txt";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Cliente> arquivoItemReader() {
        return new FlatFileItemReaderBuilder<Cliente>()
                        .name("clienteItemReader")
                        .resource(new ClassPathResource("dados-entrada.csv"))
                        .delimited()
                        .names(new String[]{"nome", "sobrenome"})
                        .fieldSetMapper(new BeanWrapperFieldSetMapper<Cliente>() {
                            {
                                this.setTargetType(Cliente.class);
                            }
                        })
                        .build();
    }

    @Bean
    public JdbcBatchItemWriter<Cliente> databaseWriter(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Cliente>()
                        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                        .sql("INSERT INTO clientes (nome, sobrenome, regiao) VALUES (:nome, :sobrenome, :regiao)")
                        .dataSource(dataSource)
                        .build();
    }

    // Passo 5
    // @Bean
    // public FlatFileItemWriter<Cliente> fileWriter() {
    // FlatFileItemWriter<Cliente> writer = new FlatFileItemWriter<Cliente>();
    // writer.setResource(new FileSystemResource(OUTPUT_FILE));
    // writer.setLineAggregator(new DelimitedLineAggregator<Cliente>() {
    // {
    // this.setDelimiter("|");
    // this.setFieldExtractor(new BeanWrapperFieldExtractor<Cliente>() {
    // {
    // this.setNames(new String[]{"nome",
    // "sobrenome", "regiao"});
    // }
    // });
    // }
    // });
    // return writer;
    // }

    // Passo 1
    // @Bean
    // public Job importarClienteArquivoJob(final NotificacaoFimJobListener notificacaoFimJobListener,
    // final Step stepChunkArquivoCliente) {
    // return this.jobBuilderFactory.get("importarClienteJob").incrementer(new RunIdIncrementer())
    // .listener(notificacaoFimJobListener)
    // .flow(stepChunkArquivoCliente).end().build();
    // }
    //
    // @Bean
    // public Step stepChunkArquivoCliente(final ItemReader<Cliente> arquivoItemReader,
    // final ItemWriter<Cliente> databaseWriter) {
    // }

    // Passo 6
    // @Bean
    // public JmsListenerContainerFactory<?> queueListenerFactory() {
    // DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    // factory.setMessageConverter(this.messageConverter());
    // return factory;
    // }
    //
    // @Bean
    // public MessageConverter messageConverter() {
    // MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    // converter.setTargetType(MessageType.TEXT);
    // converter.setTypeIdPropertyName("_type");
    // return converter;
    // }
    //
    // @Bean
    // public JmsItemReader<Cliente> jmsItemReader() {
    // JmsItemReader<Cliente> jmsItemReader = new JmsItemReader<>();
    // jmsItemReader.setJmsTemplate(this.jmsTemplate);
    // jmsItemReader.setItemType(Cliente.class);
    // return jmsItemReader;
    // }
    //
    // @Bean
    // public JobExecutionListener inserirFilaJMSListener() {
    // return new JobExecutionListener() {
    // @Override
    // public void beforeJob(final JobExecution jobExecution) {
    // Cliente[] clientes = {new Cliente("Dudu", "JMS"), new Cliente("Carluxo", "JMS"),
    // new Cliente("Tonho", "JSM")};
    // for (Cliente cliente : clientes) {
    // LOGGER.info("Inserindo na fila JMS: " + cliente.toString());
    // BatchConfiguration.this.jmsTemplate.convertAndSend(cliente);
    // }
    // }
    //
    // @Override
    // public void afterJob(final JobExecution jobExecution) {
    //
    // }
    // };
    // }

}
