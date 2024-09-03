package microservices.cli;

import io.micronaut.configuration.picocli.PicocliRunner;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import microservices.cli.videos.*;
import microservices.cli.users.*;
import microservices.cli.hashtags.*;
import microservices.cli.opinions.*;
import microservices.cli.subusers.*;
import microservices.cli.trendhashtags.*;

@Command(name = "microservice-cli", description = "...",
        mixinStandardHelpOptions = true,
                subcommands = {
                		// Video Commands
                    	GetVideoCommand.class, GetVideosCommand.class,
                    	AddVideoCommand.class, UpdateVideoCommand.class,
                    	DeleteVideoCommand.class,
                    	
                    	GetVideoViewersCommand.class, AddVideoViewerCommand.class,
                    	DeleteVideoViewerCommand.class,
                    	
                    	GetVideoHashtagsCommand.class, 
                    	AddVideoHashtagsCommand.class, AddVideoHashtagCommand.class,
                    	
                    	AddOpinionCommand.class,
                    	
                    	// User Commands
                    	GetUserCommand.class, GetUsersCommand.class,
                    	AddUserCommand.class, UpdateUserCommand.class,
                    	DeleteUserCommand.class,
                    	
                    	GetUserPostsCommand.class,
                    	GetUserHistoryCommand.class,
                    	GetUserLikesCommand.class,
                    	GetUserDislikesCommand.class,
                    	
                    	GetUserSubscriptionsCommand.class,
                    	AddUserSubscriptionsCommand.class, AddUserSubscriptionCommand.class,
                    	DeleteUserSubscriptionCommand.class,
                    	
                    	GetUserTopTenCommand.class,
                    	
                    	// Hash-tag Commands
                    	GetHashtagsCommand.class, 
                    	AddHashtagCommand.class, UpdateHashtagCommand.class,
                    	DeleteHashtagCommand.class,
                    	
                    	GetTopTenHashtagsCommand.class
                    })
public class MicroserviceCliCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(MicroserviceCliCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}